<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <!-- Add your CSS links here -->
    <!-- jQuery -->
    <script src="/resources/vendor/jquery/jquery.min.js"></script>
</head>
<body>
    <h1>Dashboard</h1>
    
    <h2>Today's Registrations</h2>
    <table border="1">
        <thead>
            <tr>
                <th>Date</th>
                <th>Member Count</th>
                               
            </tr>
        </thead>
        <tbody>
           
                <tr>
                	<td>${itemsNeedOrderCount}</td>
                    <td>${totalOrdersCount}</td>

                	<td>${receivedItemsCount}</td>
                    <td>${waitingItemsCount}</td>
               
                </tr>
           
        </tbody>
    </table>
    
    <a href="<c:url value="/admin/dashboard/totalActiveMembers"/>">View Total Active Members</a>
</body>
</html>
