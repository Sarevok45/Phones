# -*- coding: utf-8 -*-

from django.template.loader import get_template
from django.http import HttpResponse
from django.conf import settings
import datetime
import json
from django.shortcuts import redirect
from django.views.decorators.csrf import csrf_exempt


from django.shortcuts import render

# Create your views here.


def index(request):
    Fields = dict()
    template = get_template('main.html')
    html = template.render(Fields)

    return HttpResponse(html)
