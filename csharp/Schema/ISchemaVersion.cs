﻿/*******************************************************************************
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

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Expedia.Tesla.Schema
{

    /// <summary>
    /// Application schema version interface.
      
    ///  Tesla V1 use one TML file to define all versions. The schema is versioned by
    ///  a 16 bit integer value. You can specify a min/max version number for each
    ///  class or field definition.
     
    ///   Tesla V2 use totally different verioning system. Each application schema
    ///   version is defined in its own TML file. Once the version is shipped, it can't
    ///   be changed. A new version of the same objects is defined in a different copy
    ///   of TML file. Tesla compiler will merge all versions for you automatically
    ///   when the TML files are compiled. Meanwhile, compiler will generate a hash
    ///   value for each schema version. This hash value will be used to validate the
    ///   schema when it is serializing/deserializing an object.
    /// </summary>
    public interface ISchemaVersion
    {
	    /**
	    * Get the schema hash.
	    * 
	    * @return schema hash
	    */
	    long getSchemaHash();
    }

}
