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

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String type=request.getParameter("type");
		String classes=request.getParameter("Class");
		String method=request.getParameter("method");
		
		if(type.equals("Method1")){
			String methods=Controller.getMethods(1, classes);
			PrintWriter out=response.getWriter();
			out.write(methods);}
		if(type.equals("Method2")){
			String methods=Controller.getMethods(2, classes);
			PrintWriter out=response.getWriter();
			out.write(methods);}
		if(type.equals("Names1")){
			String names=Controller.getNames(1, classes);
			PrintWriter out=response.getWriter();
			out.write(names);}
		if(type.equals("Names2")){
			String names=Controller.getNames(2, classes);
			PrintWriter out=response.getWriter();
			out.write(names);}
		if(type.equals("Data")){
			String topic=request.getParameter("topic");
			String names=Controller.getDataName(classes,method,topic);
			PrintWriter out=response.getWriter();
			out.write(names);}
		if(type.equals("getController")){
			String values="";
			ArrayList list=Controller.getController();
			Iterator iterator=list.iterator();
			while(iterator.hasNext()){
				ControllerSchema controller=(ControllerSchema) iterator.next();
				values=values+controller.getClassInput()+",";
				values=values+controller.getMethodInput()+",";
				values=values+controller.getNameInput()+",";
				values=values+controller.getClassOutput()+",";
				values=values+controller.getMethodOutput()+",";
				values=values+controller.getTopic()+",";
				values=values+controller.getdata();
				if(iterator.hasNext()){
				values=values+";";}
				
			}
			PrintWriter out=response.getWriter();
			out.write(values);}
		if(type.equals("getControllerSize")){
			int size=Controller.getControllerSize();
			PrintWriter out=response.getWriter();
            out.append(""+size);
			
		}if(type.equals("getClasses1")){
			
			String size=Controller.getClassNames("Schicht1");
			PrintWriter out=response.getWriter();
            out.append(size);
			
		}
if(type.equals("getClasses2")){
			
			String size=Controller.getClassNames("Schicht2");
			PrintWriter out=response.getWriter();
            out.append(size);
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getParameter("type").equals("setController")){
		String classInput=request.getParameter("classInput");
		String methodInput=request.getParameter("methodInput"); 
		String nameInput=request.getParameter("nameInput");
		String classOutput=request.getParameter("classOutput");
		String methodOutput=request.getParameter("methodOutput");
		String data=request.getParameter("data");
		String topic=request.getParameter("topic");
		Controller.setController(classInput, methodInput, nameInput, classOutput, methodOutput,topic,data);
	}if(request.getParameter("type").equals("deleteController")){
		Controller.deleteController();
	}
	if(request.getParameter("type").equals("init")){
		Controller.init();
		}	
	if(request.getParameter("type").equals("searchMethods")){
		Controller.searchMethods();
		Controller.start();
		}	
	if(request.getParameter("type").equals("start")){
		Controller.start();
        }
    }

}
