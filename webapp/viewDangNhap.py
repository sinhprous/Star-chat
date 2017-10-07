from django.shortcuts import render
from django import forms
from webapp import models
import numpy as np
import operator
from rest_framework import viewsets
from rest_framework import permissions
from django.http import HttpResponse
from django.http import JsonResponse
# Create your views here.
from webapp.models import user
class LoginForm(forms.Form):
   user = forms.CharField(max_length = 100, label="u")
   password = forms.CharField(widget = forms.PasswordInput())

   def clean_message(self):
       username = self.cleaned_data.get("username")

       return username
def isAccept(username, password):
    u = user.objects.filter(name = username)
    print(u)
    if u != None:
        if(u[0].password == password):
            return True
    return False
def apiLogin(request):
    username = 0
    password = 0
    response_data = {}
    response_data['result'] = isAccept(username, password)
    return JsonResponse(response_data)

def index(request):
    username = "not logged in"
    if request.method == 'POST':
        # Get the posted form
        username = request.POST["u"]
        password = request.POST["p"]
        acceptAccess = isAccept(username, password)
        print("@#@!$!@#$!@$")
        print(acceptAccess)

    return render(request, 'dangnhap.html', {"username" : username})
