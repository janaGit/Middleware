/**
 * Copyright Jana Klemp
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 *
 */
package Controller;

import java.lang.reflect.Method;

public class MethodToInvoke {
    Method method;

    String topic;

    String data;

    String classes;

    MethodToInvoke(String classes, Method method, String data, String topic) {
        this.data = data;
        this.method = method;
        this.classes = classes;
        this.topic = topic;
    }

    public String getTopic()
    {
        return topic;
    }

    public Method getMethod()
    {
        return method;
    }

    public String getdata()
    {
        return data;
    }

    public String getClasses()
    {
        return classes;
    }
}
