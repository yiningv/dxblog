<section class="section">
    <div class="content">
        <#list posts as post>
            <div class="card">
                <div class="card-content">
                    <article>
                        <h1><a href="/post/${post.id}">${post.title}</a></h1>
                        <div class="content">
                            ${post.description}
                        </div>
                        <time datetime="2016-1-1">11:09 PM - 1 Jan 2016</time>
                    </article>
                </div>
            </div>
        </#list>
    </div>
</section>