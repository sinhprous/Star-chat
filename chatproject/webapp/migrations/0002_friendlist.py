# -*- coding: utf-8 -*-
# Generated by Django 1.10.6 on 2017-10-07 17:37
from __future__ import unicode_literals

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('webapp', '0001_initial'),
    ]

    operations = [
        migrations.CreateModel(
            name='friendlist',
            fields=[
                ('flid', models.BigAutoField(primary_key=True, serialize=False)),
                ('user_id1', models.BigIntegerField()),
                ('user_id2', models.BigIntegerField()),
            ],
        ),
    ]
