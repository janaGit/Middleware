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

package Controller;

public class ControllerSchema {
    String classInput;

    String methodInput;

    String nameInput;

    String classOutput;

    String methodOutput;

    String topic;

    String data;

    public String getdata()
    {
        return data;
    }

    public void setdata(String data)
    {
        this.data = data;
    }

    public void setClassInput(String classInput)
    {
        this.classInput = classInput;
    }

    public void setMethodInput(String methodInput)
    {
        this.methodInput = methodInput;
    }

    public void setNameInput(String nameInput)
    {
        this.nameInput = nameInput;
    }

    public void setClassOutput(String classOutput)
    {
        this.classOutput = classOutput;
    }

    public void setMethodOutput(String methodOutput)
    {
        this.methodOutput = methodOutput;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public String getClassInput()
    {
        return classInput;
    }

    public String getMethodInput()
    {
        return methodInput;
    }

    public String getNameInput()
    {
        return nameInput;
    }

    public String getClassOutput()
    {
        return classOutput;
    }

    public String getMethodOutput()
    {
        return methodOutput;
    }

    public String getTopic()
    {
        return topic;
    }
}
