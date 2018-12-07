<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ page import="src.pet_manage.model.BeanTbl_Pet" %>
<%@ page import="" %>
<%@ page import="pet_manage.control.PetManager" %>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Show Table</title>
</head>
<style>
    .body{
        margin-left:300px;
        margin-top:100px
    }
    .body > table {
        height:200px;
        width:400px;
    }
    td{
        text-align:center;
    }

</style>
<body>
<div class="body">
    <table border="1">
        <tr >
            <td>Id</td>
            <td>Name</td>
            <td>Age</td>
            <td>Sex</td>
        </tr>
        <%
            try {
            PetManager m=new PetManager();
            List<BeanTbl_Pet> list =m.loadAllPets1();
            for(BeanTbl_Pet tl:list)
            {%>
        <tr>
            <td><%=tl.getPet_id() %></td>
            <td><%=tl.getName() %></td>
            <td><%=tl.getMaster_id() %></td>
            <td><%=tl.getRemark() %></td>
        </tr>
        <%}}
        catch (Exception e){
                e.printStackTrace();
        }
        %>
    </table>
</div>
</body>
</html>