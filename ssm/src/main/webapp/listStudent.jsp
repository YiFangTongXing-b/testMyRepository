<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>
<html>
<head>
    <title>查询学生ajax</title>
    <base href="<%=basePath%>"/>
    <script type="text/javascript" src="js/jquery-3.4.1.js"></script>
    <script type="text/javascript">
        $(function () {
            loadStudent();
            $("#btn").click(function (){
                loadStudent();
            })
        })

        function loadStudent(){
            $.ajax({
                url:"student/queryStudent.do",
                type:"get",
                dataType:"json",
                success:function (resp) {
                    //清除旧数据
                    $("#info").html("");
                    $.each(resp,function (i,n){
                        $("#info").append("<tr>")
                            .append("<td>"+n.id+"</td>")
                            .append("<td>"+n.name+"</td>")
                            .append("<td>"+n.age+"</td>")
                            .append("<td/>")
                    })
                }
            })
        }
    </script>
</head>
<body>
    <div align="center">
        <table cellspacing="0" border="1px">
            <thead>
             <tr>
                 <td>学号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                 <td>姓名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                 <td>年龄</td>

             </tr>
            </thead>
            <tbody id="info">

            </tbody>
        </table>
        <input type="button" id="btn" value="查询数据" >
    </div>
</body>
</html>
