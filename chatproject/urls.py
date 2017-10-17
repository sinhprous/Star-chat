"""chatproject URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/1.10/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  url(r'^$', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  url(r'^$', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.conf.urls import url, include
    2. Add a URL to urlpatterns:  url(r'^blog/', include('blog.urls'))
"""
from django.conf.urls import url, include
from django.contrib import admin
from webapp import viewDangNhap, viewDangKy, viewQuenPass, viewstatus

urlpatterns = [
    url(r'^admin/', admin.site.urls),
    url(r'^login/', viewDangNhap.index, name="index"),
    url(r'^api/login/', viewDangNhap.api_login, name="index"),
    url(r'^register/', viewDangKy.index, name="register"),
    url(r'^api/register/', viewDangKy.apiRegister, name="index"),
    url(r'^forgetpass/', viewQuenPass.index, name="index"),
    url(r'^api/forgetpass/', viewQuenPass.api_forget_pass, name="index"),
    url(r'^status/', viewstatus.index, name="index"),
]

