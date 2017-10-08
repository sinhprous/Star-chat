from django.shortcuts import render
from django import forms
from django.views.decorators.csrf import csrf_exempt
from django.db import transaction
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


class RegisterForm(forms.Form):
    user = forms.CharField(max_length = 100, label="email")
    password = forms.CharField(widget = forms.PasswordInput())
    def clean_message(self):
       username = self.cleaned_data.get("username")

       return username
def isInsertDB(request):
    response_data = {}
    if request.method == "POST":
        e = user.objects.filter(email=request.POST["email"])
        if len(e) > 0:
            response_data['result'] = "true"
            dangky()
        else:
            response_data['result'] = "false"
    return response_data
@transaction.atomic
def dangky(request):
    user_entry = user()
    user_entry.email = request.POST["email"]
    user_entry.password = request.POST["password"]
    user_entry.name = request.POST["name"]
    user_entry.save()
    print(user_entry)


@transaction.atomic
def dangky2(received_json_data): ##Dung cho API
    user_entry = user()
    user_entry.email = received_json_data.get("email", "#")
    user_entry.password = received_json_data.get("password", "#")
    user_entry.name = received_json_data.get("name", "#")
    user_entry.save()
    print(user_entry)

# Cau truc JSON
# {
#   'name':,
#   'password':,
#   'email':,
# }
@csrf_exempt
def apiRegister(request):
    response_data = {}
    received_json_data = json.loads(request.body.decode("utf-8"))
    if request.method == 'POST':
        email = received_json_data.get("email", False)
        if (len(user.objects.filter(email=email)) > 0 ):
            response_data['result'] = "false"
        else:
            response_data['result'] = "true"
            dangky2(received_json_data)
    return JsonResponse(response_data)


def index(request):
    email = "not logged in"
    if request.method == 'POST':
        # Get the posted form
        email = request.POST["email"]
        password = request.POST["password"]
        flag = isInsertDB(request)

    return render(request, 'dangky.html')

