from django.shortcuts import render
from django.http import HttpResponse
from django.shortcuts import render
from django import forms
import numpy as np
import operator
import os
# Create your views here.
def index(request):
    num_request = request
    if request.method == "POST":
        username = forms.CharField(label='u')
        password = forms.CharField(label='p')
        print(username, password)
        return render(num_request, "dangnhap.html")
    return render(request, "dangnhap.html")