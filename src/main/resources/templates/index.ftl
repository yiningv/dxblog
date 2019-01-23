<section class="section">
    <div class="content">
        <#list posts as post>
            <div class="box">
                <p class="title">
                    <a href="/post/${post.id}">${post.title}</a>
                </p>
                <article class="message is-info">
                    <div class="message-body">
                        <p>${post.description}</p>
                    </div>
                </article>
                <div class="ifoot">
                    <nav class="level is-mobile">
                        <div class="level-left">
                            <#list post.tags as tag>
                                <a class="level-item" href="/tag/${tag}">
                                <span class="tag is-info"><span class="icon is-small"><i class="fa fa-tag"></i>&nbsp;</span>${tag} </span>
                                </a>
                            </#list>
                        </div>
                        <div class="level-right">
                            <#assign date = post.created?string('yyyy-MM-dd')>
                            <time datetime="${date}">${date}</time>
                        </div>
                    </nav>
                </div>
            </div>
        </#list>
    </div>
    <#if pageInfo??>
        <#include "_partial/pagination.ftl">
    </#if>
</section>