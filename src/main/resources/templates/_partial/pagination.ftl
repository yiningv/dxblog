<#if pageInfo??>
    <nav class="pagination is-centered">
    <#if pageInfo.prev>
        <a class="button is-info pagination-previous" href="/prev" >
            <span class="icon"><i class="fa fa-chevron-left"></i></span><span>Prev</span>
        </a>
    </#if>
    <#if pageInfo.next>
        <a class="button is-info pagination-next" href="/next" >
            <span>Next</span> <span class="icon"><i class="fa fa-chevron-right"></i></span>
        </a>
    </#if>
    </nav>
</#if>