<html>
<head>
    <content tag="pagetitle">User Summary: ${targetUser.username?html}</content>
    <content tag="pageID">usergroups-searchusers</content>
    <content tag="pagehelp">
        <h3>Help Section Here</h3>

        <p>
            View and edit user properties using the form below. Other useful information is provided as well
            such as last post time and group memberships.
        </p>
    </content>
<body>
<table cellpadding="3" cellspacing="0" border="0" width="100%">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>key</th>
    </tr>
    </thead>
    <tbody>
    <#list externalapps as ea >
    <tr>
        <td>${ea.id}</td>
        <td>${ea.name}</td>
        <td>${ea.key}</td>
    </tr>
    </#list>
    </tbody>
</table>
</body>
</html>