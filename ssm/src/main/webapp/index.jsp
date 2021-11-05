<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
    <link rel="shortcut icon" href="#" />
    <title>功能入口</title>
    <base href="<%=basePath%>"/>
</head>
<body>
   <div align="center">
       <h3>ssm项目</h3>
       <img src="images/ssm.jpg"/>
       <br/>
       <br/>
       <table>
           <tr>
               <td><a href="addStudent.jsp">注册学生</a></td>
           </tr>
           <tr>
               <td><a href="listStudent.jsp">浏览学生</a></td>
           </tr>
       </table>

   </div>
</body>
</html>