from django.shortcuts import render
from django import forms
from django.views.decorators.csrf import csrf_exempt
import json
from django.db import transaction
from webapp import models
import numpy as np
import operator
from rest_framework import viewsets
from rest_framework import permissions
from django.http import HttpResponse
from django.http import JsonResponse
# Create your views here.
from webapp.models import user


class ForgetForm(forms.Form):
    user = forms.CharField(max_length = 100, label="u")
    password = forms.CharField(widget = forms.PasswordInput())

    def clean_message(self):
        username = self.cleaned_data.get("username")
        return username


def is_exist_mail(email):
    e = user.objects.filter(email = email)
    if e != None:
        return True
    return False


@transaction.atomic
def android_forget_pass(received_json_data): ##Dung cho API
    user_entry = user()
    user_entry.email = received_json_data.get("email", "#")

@csrf_exempt
def api_forget_pass(request):
    response_data = {}
    # Cau truc JSON
    # {
    #   'email':,
    # }
    received_json_data = json.loads(request.body.decode("utf-8"))
    if request.method == 'POST':
        email = received_json_data.get("email", False)
        if ( len(user.objects.filter(email=email)) > 0):
            response_data['result'] = "false"
        else:
            response_data['result'] = "true"
        response_data['result'] = is_exist_mail(email)
    return JsonResponse(response_data)


def index(request):
    email = ""
    if request.method == 'POST':
        # Get the posted form
        email = request.POST["e"]
        acceptForget = is_exist_mail(email)
        if acceptForget:
            return render(request, "acceptforget.html")
        else:
            return render(request, "success.html")
    return render(request, 'quenmatkhau.html', {"username" : email})
