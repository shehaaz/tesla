/*******************************************************************************
 * Copyright (c) 2014 Expedia Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package com.expedia.tesla.compiler.plugins;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang.StringEscapeUtils;

import com.expedia.tesla.compiler.Util;
import com.expedia.tesla.schema.Array;
import com.expedia.tesla.schema.Enum;
import com.expedia.tesla.schema.Field;
import com.expedia.tesla.schema.Nullable;
import com.expedia.tesla.schema.Primitive;
import com.expedia.tesla.schema.Reference;
import com.expedia.tesla.schema.Type;
import com.expedia.tesla.schema.UserType;

/**
 * @author <a href="mailto:yzuo@expedia.com">Yunfei Zuo</a>
 * 
 */
public class CSharpUtils extends Util {

	private static Map<String, String> typeMap = new TreeMap<String, String>();

	static {
		typeMap.put(Primitive.BYTE.getName(), "byte");
		typeMap.put(Primitive.INT16.getName(), "short");
		typeMap.put(Primitive.INT32.getName(), "int");
		typeMap.put(Primitive.INT64.getName(), "long");
		typeMap.put(Primitive.UINT16.getName(), "ushort");
		typeMap.put(Primitive.UINT32.getName(), "uint");
		typeMap.put(Primitive.UINT64.getName(), "ulong");
		typeMap.put(Primitive.BOOLEAN.getName(), "bool");
		typeMap.put(Primitive.FLOAT.getName(), "float");
		typeMap.put(Primitive.DOUBLE.getName(), "double");
		typeMap.put(Primitive.STRING.getName(), "string");
		typeMap.put(Primitive.BINARY.getName(), "byte[]");
	}

	private String getCSharpFieldTypeName(Field field) {
		Type type = field.getType();
		return getTypeName(type);
	}

	private String getTypeName(Type type) {
		if (type.isPrimitive()) {
			return typeMap.get(((Primitive) type).getName());
		} else if (type.isUserType()) {
			return ((UserType) type).getName();
		} else if (type.isArray()) {
			return getTypeName(((Array) type).getElementType());
		} else if (type.isNullable()) {
			return getTypeName(((Nullable) type).getElementType());
		} else if (type.isReference()) {
			return getTypeName(((Reference) type).getElementType());
		} else {
			throw new RuntimeException("Unable to map type " + type.getTypeId());
		}
	}

	public String getCSharpTypeName(Field field) {
		String elementType = getCSharpFieldTypeName(field);

		return getCSharpListType(elementType, field.getRank());
	}

	public String getCSharpListType(String typeName, int rank) {
		if (rank < 1) {
			return typeName;
		} else if (rank == 1) {
			return String.format("List<%s>", typeName);
		} else {
			return String.format("List<%s>", getCSharpListType(typeName, rank - 1));
		}
	}

	public String getWriteMethod(Field field) {
		return getReadWriteMethod(field, "Write");
	}

	public String getReadMethod(Field field) {
		return getReadWriteMethod(field, "Read");
	}
	
	private String getReadWriteMethod(Field field, String rw) {
		if (field.getRank() == 0) {
			// Non-array.
			if (field.getType() instanceof Enum) {
				return String.format("%s<%s>", rw, getCSharpFieldTypeName(field));
			} else {
				return rw;
			}
		} else {
			// Arrays
			if (field.getType() instanceof Primitive) {
				return String.format("%s%sArray", rw, ((Primitive) field.getType()).getName());
			} else if (field.getType() instanceof Enum) {
				return String.format("%sEnum<%s>", rw, getCSharpFieldTypeName(field));
			} else {
				return String.format("%sObjectArray<%s>", rw, getCSharpFieldTypeName(field));
			}
		}
	}

	public String escapeStringLiteral(String str) {
		return '\"' + StringEscapeUtils.escapeJava(str) + '\"';
	}

}
