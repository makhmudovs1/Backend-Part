<!DOCTYPE html>
<html>
<head>
    <title>Plants</title>
</head>
<body>
<h1>Plants</h1><hr>
<h4>Подробная информация о товаре</h4>
<#list images as img>
    <img src="/images/${img.id}" height="60px"/><br><br>
</#list>
<b>Название товара: </b>${product.title}<br>
<b>Описание товара: </b>${product.description}<br>
<b>Цена: </b>${product.price}<br>
<b>Город: </b>${product.city}<br>
<b>Автор: </b>
<#if product.user?has_content>
    <a href="/user/${product.user.id}">${product.user.name}</a>
<#else>
    Нет информации об авторе
</#if><br>
</body>
</html>
