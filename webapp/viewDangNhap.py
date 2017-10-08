from django.shortcuts import render
from django import forms
from django.views.decorators.csrf import csrf_exempt
import json
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
def isAccept(email, password):
    e = user.objects.filter(email = email)
    if e != None:
        if(e[0].password == password):
            return True
    return False
@csrf_exempt
def apiLogin(request):
    response_data = {}
    received_json_data = json.loads(request.body.decode("utf-8"))
    if request.method == 'POST':
        for x in request.POST:
            print(x)
        username = received_json_data.get("email", False)
        password = received_json_data.get("password", False)

        print(username)
        print(password)


        response_data['result'] = isAccept(username, password)
    return JsonResponse(response_data)

def index(request):
    email = "not logged in"
    if request.method == 'POST':
        # Get the posted form
        email = request.POST["e"]
        password = request.POST["p"]
        acceptAccess = isAccept(email, password)


    return render(request, 'dangnhap.html', {"username" : email})
