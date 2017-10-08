from django.conf.urls import url
from . import views

urlpatterns = [
    url(r'^$', views.index, name='index'),
    url(r'^(<question_id>[0-9]+)/$', views.detail, name='detail'),
    url(r'^(<question_id>[0-9]+)/result$', views.results, name='result'),
    url(r'^(<question_id>[0-9]+)/vote$', views.vote, name='vote'),
]