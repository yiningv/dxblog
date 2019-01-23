<#if pageInfo??>
    <nav class="pagination is-centered">
    <#if pageInfo.prev>
        <a class="button is-info pagination-previous" href="/index?page=${pageInfo.current-1}">
            <span class="icon"><i class="fa fa-chevron-left"></i></span><span>Prev</span>
        </a>
    <#else>
        <a class="button is-info pagination-previous is-disabled">
            <span class="icon"><i class="fa fa-chevron-left"></i></span><span>Prev</span>
        </a>
    </#if>
    <#if pageInfo.next>
        <a class="button is-info pagination-next" href="/index?page=${pageInfo.current+1}" >
            <span>Next</span> <span class="icon"><i class="fa fa-chevron-right"></i></span>
        </a>
    <#else>
        <a class="button is-info pagination-next is-disabled" >
            <span>Next</span> <span class="icon"><i class="fa fa-chevron-right"></i></span>
        </a>
    </#if>
    </nav>
</#if>