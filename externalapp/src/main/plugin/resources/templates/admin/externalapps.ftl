<html>
<head>
    <content tag="pagetitle">External Apps</content>
    <content tag="pageID">externalapp-applist</content>
    <content tag="pagehelp">
        <h3>Help Section Here</h3>
    </content>
<body>
<#--<#list action?keys as key> ${key} <br/></#list>-->

<table cellpadding="3" cellspacing="0" border="0" width="100%">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>key</th>
    </tr>
    </thead>
    <tbody>

    <#if action.getExternalApps()?has_content>
        <#list action.getExternalApps() as ea >
        <tr>
            <td>${ea.id}</td>
            <td>${ea.name}</td>
            <td>${ea.key}</td>
        </tr>
        </#list>
    </#if>
    </tbody>
</table>
</body>
</html>