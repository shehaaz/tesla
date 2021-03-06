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
package com.expedia.tesla.serialization.binary;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import com.expedia.tesla.serialization.DoubleFixture;

/**
 * Serialization Double tests for the Tesla Binary format.
 * 
 * @author dheld
 */
public class DoubleSerializationTest extends BaseFixture implements
		DoubleFixture {
	@Test(expected = java.io.IOException.class)
	public void writeDouble_StreamIOException_Throws() throws Exception {
		this.getThrowWriter().writeDouble(null, POSITIVE_VALUE);
	}

	@Test
	public void writeDouble_Negative_Succeeds() throws Exception {
		this.getWriter().writeDouble(null, NEGATIVE_VALUE);
		assertArrayEquals(NEGATIVE_BINARY, this.getOutput());
	}

	@Test
	public void writeDouble_Positive_Succeeds() throws Exception {
		this.getWriter().writeDouble(null, POSITIVE_VALUE);
		assertArrayEquals(POSITIVE_BINARY, this.getOutput());
	}

	@Test
	public void writeDouble_TinyValue_Succeeds() throws Exception {
		this.getWriter().writeDouble(null, TINY_VALUE);
		assertArrayEquals(TINY_BINARY, this.getOutput());
	}

	@Test
	public void writeDouble_MinValue_Succeeds() throws Exception {
		this.getWriter().writeDouble(null, MIN_VALUE);
		assertArrayEquals(MIN_BINARY, this.getOutput());
	}

	@Test
	public void writeDouble_MaxValue_Succeeds() throws Exception {
		this.getWriter().writeDouble(null, MAX_VALUE);
		assertArrayEquals(MAX_BINARY, this.getOutput());
	}

	@Test
	public void writeDouble_NegInfValue_Succeeds() throws Exception {
		this.getWriter().writeDouble(null, NEG_INF_VALUE);
		assertArrayEquals(NEG_INF_BINARY, this.getOutput());
	}

	@Test
	public void writeDouble_PosInfValue_Succeeds() throws Exception {
		this.getWriter().writeDouble(null, POS_INF_VALUE);
		assertArrayEquals(POS_INF_BINARY, this.getOutput());
	}

	@Test
	public void writeDouble_NaNValue_Succeeds() throws Exception {
		this.getWriter().writeDouble(null, NAN_VALUE);
		assertArrayEquals(NAN_BINARY, this.getOutput());
	}

}
