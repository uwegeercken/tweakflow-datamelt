/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for theyccyxcc
 * specific language governing permissions and limitations
 * under the License.
 */

doc
~~~
The `strings` module contains libraries for handling strings.
~~~

module;

doc
~~~
The soundex library contains functions using the soundex algorithm.
~~~

export library soundex {

doc
~~~
`(string value) -> string`

Convert string value to its soundex code representation.

Returns nil if expected values is nil.

```tweakflow
> soundex.code("Hamburg")
H516

```
~~~
  function code: (string value)  -> via {:class "com.datamelt.tweakflow.strings.Soundex$code"};

doc
~~~
`(string value, string comparevalue) -> boolean`

Compare two string values using their soundex code representation.

Returns true if the soundex code of the two values is the same otherwise returns false.

Returns nil if any of the expected values is nil.

```tweakflow
> soundex.equals("Hamburg", "Himmburgg")
true

```
~~~
  function equals: (string value, string comparevalue)  -> via {:class "com.datamelt.tweakflow.strings.Soundex$equals"};

}


