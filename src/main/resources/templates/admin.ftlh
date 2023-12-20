<!DOCTYPE html>
<html>
<head>
    <title>Админ-панель</title>
</head>
<body>
<h1>Админ-панель</h1>
<table border="1">
    <tr>
        <th>Email</th>
        <th>Роли</th>
        <th>Действия</th>
    </tr>
    <#list users as user>
        <tr>
            <td>${user.email}</td>
            <td><#list user.roles as role>${role}, </#list></td>
            <td>
                <form action="/admin/user/edit/${user.id}" method="get">
                    <button type="submit">Редактировать роли</button>
                </form>
            </td>
        </tr>
    </#list>
</table>
</body>
</html>
