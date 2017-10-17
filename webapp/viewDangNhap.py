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


def is_accept(email, password):
    e = user.objects.filter(email = email)
    if (e.count()==1):
        if(e[0].password == password):
            for i in e:
                i.status = True
                i.save()
            return True
    return False


@csrf_exempt
def api_login(request):
    response_data = {}
    received_json_data = json.loads(request.body.decode("utf-8"))
    if request.method == 'POST':
        username = received_json_data.get("email", False)
        password = received_json_data.get("password", False)
        response_data['result'] = is_accept(username, password)
    return JsonResponse(response_data)


def index(request):
    email = "01923812"
    if request.method == 'POST':
        # Get the posted form
        email = request.POST["e"]
        password = request.POST["p"]
        print(email, password)
        acceptAccess = is_accept(email, password)
        if acceptAccess:
            return render(request, 'success.html')
        else:
            return render(request, 'fail.html')
    return render(request, 'dangnhap.html', {"username" : email})
