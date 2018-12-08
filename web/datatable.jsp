<%@ page import="reader.ReadFilePath" %>
<%@ page import="model.BeanCrime" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="hibernate_test.CrimeManager" %>
<%@ page import="hibernate_test.PrisonerManager" %>
<%@ page import="hibernate_test.DrugManager" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="hibernate_test.HibernateUtil" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="model.BeanPrisoner" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();

    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>数据表格</title>

    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="css/datatables.min.css" rel="stylesheet">
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
</head>

<body>

<div id="wrapper">

    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav metismenu" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span>
                            <img src="img/龙猫.png" class="img-circle" alt="img">
                        </span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                            <span class="clear"> <span class="block m-t-xs"> <strong class="font-bold">青铜小姐姐</strong>
                             </span> <span class="text-muted text-xs block">管理员 <b class="caret"></b></span> </span> </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li><a href="index.jsp">登出</a></li>
                        </ul>
                    </div>
                    <div class="logo-element">
                        IN+
                    </div>
                </li>
                <li >
                    <a href="homepage.jsp"><i class="fa fa-th-large"></i> <span class="nav-label">主页</span></a>
                </li>
                <li class="active">
                    <a href="datatable.jsp"><i class="fa fa-th-large"></i> <span class="nav-label">表格</span></a>
                </li>
                <li>
                    <a href="graph_flot.jsp"><i class="fa fa-bar-chart-o"></i> <span class="nav-label">flot图表</span> </a>
                </li>
                <li>
                    <a href="answer.jsp"><i class="fa fa-diamond"></i> <span class="nav-label">简单问答</span> </a>
                </li>
                <li>
                    <a href="d3.jsp"><i class="fa fa-diamond"></i> <span class="nav-label">d3</span> </a>
                </li>
                <li >
                    <a href="gragh_label.jsp"><i class="fa fa-diamond"></i> <span class="nav-label">graghlabel</span> </a>

                </li>
            </ul>

        </div>
    </nav>

    <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                </div>
                <ul class="nav navbar-top-links navbar-right">
                    <li>
                        <span class="m-r-sm text-muted welcome-message">小姐姐的世界</span>
                    </li>
                    <li>
                        <a href="index.jsp">
                            <i class="fa fa-sign-out"></i> 退出
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
        <div class="row wrapper border-bottom white-bg page-heading">
            <div class="col-lg-10">
                <h2>数据表格</h2>
                <%--<ol class="breadcrumb">--%>
                    <%--<li>--%>
                        <%--<a href="index.html">主页</a>--%>
                    <%--</li>--%>
                    <%--<li>--%>
                        <%--<a>表格</a>--%>
                    <%--</li>--%>
                    <%--<li class="active">--%>
                        <%--<strong>数据表格</strong>--%>
                    <%--</li>--%>
                <%--</ol>--%>
            </div>
            <div class="col-lg-2">
            </div>
        </div>
        <div class="wrapper wrapper-content animated fadeInRight">
            <div class="row">
                <div class="col-lg-12">
                    <div class="ibox float-e-margins">
                        <div class="ibox-title">
                            <div class="ibox-tools">
                                <a class="collapse-link">
                                    <i class="fa fa-chevron-up"></i>
                                </a>
                            </div>
                        </div>
                        <div class="ibox-content">

                            <div class="table-responsive" style="overflow:scroll;">
                                <table class="table table-striped  table-bordered table text-nowrap table-hover dataTables-example" style="min-width:1500px;">
                                    <thead>
                                    <tr>
                                        <th>案号</th>
                                        <th>法院名称</th>
                                        <th>地区</th>
                                        <th>时间</th>
                                        <th>一案人数</th>

                                        <th>年龄最小人员出生日期</th>
                                        <th>第一被告姓名</th>
                                        <th>性别</th>
                                        <th>身份证</th>
                                        <th>民族</th>

                                        <th>文化程度</th>
                                        <th>职业</th>
                                        <th>户籍</th>
                                        <th>罪名</th>
                                        <th>刑罚种类</th>

                                        <th>刑期</th>
                                        <th>财产刑种类</th>
                                        <th>财产刑金额</th>
                                        <th>毒品种类和数量或单位</th>
                                        <th>毒品单价</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                                            SimpleDateFormat bakDateFormat = new SimpleDateFormat("yyyy年M月d日");

                                            List<BeanCrime> list=CrimeManager.loadAllCrimes();

                                            for(BeanCrime tl:list)
                                            {
                                                String crimeDate="";
                                                String minPrisonerBirth="";
                                                BeanPrisoner firstPrisoner = PrisonerManager.getPrisoner(tl.getFirstprisonerid());
                                                try{
                                                    crimeDate = dateFormat.format(tl.getDate());
                                                }
                                                catch (Exception e)
                                                {
                                                    try {
                                                        crimeDate=bakDateFormat.format(tl.getDate());
                                                    }
                                                    catch (Exception e1){
                                                        crimeDate="";
                                                    }
                                                }
                                                try{
                                                    minPrisonerBirth = dateFormat.format(tl.getMinimumAge());
                                                }
                                                catch (Exception e)
                                                {
                                                    try {
                                                        minPrisonerBirth = bakDateFormat.format(tl.getFirstPrisoner().getBirth());
                                                    }
                                                    catch (Exception e1)
                                                    {
                                                        minPrisonerBirth="";
                                                    }
                                                }
                                    %>
                                    <tr>
                                        <td><%=tl.getSerial() %></td>
                                        <td><%=tl.getProcuratorate()%></td>
                                        <td><%=tl.getArea() %></td>

                                        <td><%=crimeDate %></td>
                                        <td><%=tl.getPrisonersSet().size() %></td>

                                        <td><%=minPrisonerBirth%></td>
                                        <td><%=firstPrisoner.getName() %></td>
                                        <td><%=firstPrisoner.getSex() %></td>
                                        <td><%=firstPrisoner.getIdCard() %></td>
                                        <td><%=firstPrisoner.getNation()%></td>

                                        <td><%=firstPrisoner.getLevel() %></td>
                                        <td><%=firstPrisoner.getWork() %></td>
                                        <td><%=firstPrisoner.getPlace() %></td>
                                        <td><%=firstPrisoner.getCrime()%></td>
                                        <td><%=firstPrisoner.getPrisonType() %></td>

                                        <td><%=firstPrisoner.getPrisonTime() %></td>
                                        <td><%=firstPrisoner.getPenalty() %></td>
                                        <td><%=Float.toString(firstPrisoner.getPenaltySum())%></td>
                                        <td><%=tl.sqlShowDrugs() %></td>
                                        <td><%=tl.sqlShowAverageDrugs() %></td>


                                    </tr>
                                    <%
                                            }
                                    %>
                                    </tbody>

                                </table>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>


    </div>
</div>



<!-- Mainly scripts -->
<script src="js/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<script src="js/plugins/dataTables/datatables.min.js"></script>

<!-- Custom and plugin javascript -->
<script src="js/inspinia.js"></script>
<script src="js/plugins/pace/pace.min.js"></script>
<script>
    $(document).ready(function(){
        $('.dataTables-example').DataTable({
            pageLength: 25,
            responsive: true,
            dom: '<"html5buttons"B>lTfgitp',
            buttons: [
                { extend: 'copy'},
                {extend: 'csv'},
                {extend: 'excel', title: 'ExampleFile'},
                {extend: 'pdf', title: 'ExampleFile'}
            ]
        });

    });

</script>

</body>

</html>
