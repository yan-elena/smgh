---
layout: default
---

<div class="home">
  {%- if page.title -%}
    <h1 class="page-heading">{{ page.title }}</h1>
  {%- endif -%}

    <body>
        <header class="row">
            <h1 class="col-10 text-center">Smart Greenhouse - pps-2022-smartgh</h1>
            <div class="col-2 pt-2"><a class="btn btn-dark" id="forkme_banner" href="https://github.com/AnnaVitali/PPS-22-smartgh">View on GitHub</a></div>
        </header>

      <div class="row m-4">
          <section class="inner">
              <div class="row">
                  <h2 class="col-10 text-center">Progetto per il corso di Paradigmi di Programmazione e Sviluppo</h2>
                  <div class="row m-3">
                      <div class="col-10">
                          <div class="d-flex justify-content-center">
                              <ul class="list-group list-group-horizontal justify-content-center">
                                  <li class="list-group-item" style="border: none;" >Autori: </li>
                                  <li class="list-group-item" style="border: none;">Folin Veronika</li>
                                  <li class="list-group-item" style="border: none;">Mengozzi Maria</li>
                                  <li class="list-group-item" style="border: none;">Vitali Anna</li>
                                  <li class="list-group-item" style="border: none;">Yan Elena</li>
                              </ul>
                          </div>
                      </div>
                  </div>
              </div>
              <div class="row">
                  <h3 class="col-12 text-center">Indice</h3>
                  <div class="d-flex justify-content-center">
                      <div class="list-group col-2">
                          <a href="00_introduction.html" class="list-group-item list-group-item-action">Introduzione</a>
                          <a href="01_development_process.html" class="list-group-item list-group-item-action">Processo di sviluppo</a>
                          <a href="02_requirements.html" class="list-group-item list-group-item-action">Requisiti</a>
                          <a href="03_architectural_design.html" class="list-group-item list-group-item-action">Design architetturale</a>
                          <a href="04_detail_design.html" class="list-group-item list-group-item-action">Design dettagliato</a>
                          <a href="05_implementation.html" class="list-group-item list-group-item-action">Implementazione</a>
                          <a href="06_retrospective.html" class="list-group-item list-group-item-action">Retrospettiva</a>
                          <a href="07_conclusions.html" class="list-group-item list-group-item-action">Conclusioni</a>
                          <a href="08_user_guide.html" class="list-group-item list-group-item-action">Guida utente</a>
                      </div>
                  </div>
              </div>

          </section>
      </div>
    </body>


  {% if site.paginate %}
    {% assign posts = paginator.posts %}
  {% else %}
    {% assign posts = site.posts %}
  {% endif %}


  {%- if posts.size > 0 -%}
    {%- if page.list_title -%}
      <h2 class="post-list-heading">{{ page.list_title }}</h2>
    {%- endif -%}
    <ul class="post-list">
      {%- assign date_format = site.minima.date_format | default: "%b %-d, %Y" -%}
      {%- for post in posts -%}
      <li>
        <span class="post-meta">{{ post.date | date: date_format }}</span>
        <h3>
          <a class="post-link" href="{{ post.url | relative_url }}">
            {{ post.title | escape }}
          </a>
        </h3>
        {%- if site.show_excerpts -%}
          {{ post.excerpt }}
        {%- endif -%}
      </li>
      {%- endfor -%}
    </ul>

    {% if site.paginate %}
      <div class="pager">
        <ul class="pagination">
        {%- if paginator.previous_page %}
          <li><a href="{{ paginator.previous_page_path | relative_url }}" class="previous-page">{{ paginator.previous_page }}</a></li>
        {%- else %}
          <li><div class="pager-edge">•</div></li>
        {%- endif %}
          <li><div class="current-page">{{ paginator.page }}</div></li>
        {%- if paginator.next_page %}
          <li><a href="{{ paginator.next_page_path | relative_url }}" class="next-page">{{ paginator.next_page }}</a></li>
        {%- else %}
          <li><div class="pager-edge">•</div></li>
        {%- endif %}
        </ul>
      </div>
    {%- endif %}

  {%- endif -%}

</div>
