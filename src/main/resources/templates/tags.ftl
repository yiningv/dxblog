<div>
    <#list tags as tag>
        <a href="/tag/${tag.name}" title="${tag.name}" rel="${tag.count}">${tag.name}</a>
    </#list>
</div>