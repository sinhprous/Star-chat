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


def online_counter():
    e = user.objects.filter(status = True)
    return e.count()

def api_status(request):
    pass

def index(request):
    count = online_counter()
    # email = ""
    # if request.method == 'POST':
    #     # Get the posted form
    #     count = online_counter()
    #     if acceptForget:
    #         return render(request, "acceptforget.html")
    #     else:
    #         return render(request, "success.html")
    return render(request, 'checkstatus.html', {"count" : count})