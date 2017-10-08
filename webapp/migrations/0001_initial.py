# -*- coding: utf-8 -*-
# Generated by Django 1.10.6 on 2017-10-07 14:51
from __future__ import unicode_literals

from django.db import migrations, models
import django.db.models.deletion


class Migration(migrations.Migration):

    initial = True

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='conversation',
            fields=[
                ('conversation_id', models.BigAutoField(primary_key=True, serialize=False)),
                ('privacy', models.IntegerField(default=0)),
            ],
        ),
        migrations.CreateModel(
            name='messages',
            fields=[
                ('message_id', models.BigAutoField(primary_key=True, serialize=False)),
                ('content', models.CharField(max_length=5000)),
                ('timestamp_sent', models.BigIntegerField(default=0)),
                ('privacyMessageLevel', models.IntegerField(default=0)),
                ('numreaded', models.IntegerField(default=0)),
                ('fileup', models.FileField(upload_to='uploads/%Y/%m/%d/')),
                ('conversation_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='webapp.conversation')),
            ],
        ),
        migrations.CreateModel(
            name='paticipants',
            fields=[
                ('paticipant_id', models.BigAutoField(primary_key=True, serialize=False)),
                ('conversation_id', models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='webapp.conversation')),
            ],
        ),
        migrations.CreateModel(
            name='user',
            fields=[
                ('id', models.BigAutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=300)),
                ('email', models.CharField(max_length=300)),
                ('password', models.CharField(max_length=300)),
                ('frendlist_id', models.BigIntegerField()),
                ('block_id', models.BigIntegerField()),
                ('status', models.BooleanField(default=False)),
            ],
        ),
        migrations.AddField(
            model_name='paticipants',
            name='user_id',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='webapp.user'),
        ),
        migrations.AddField(
            model_name='messages',
            name='from_id',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='webapp.user'),
        ),
        migrations.AddField(
            model_name='conversation',
            name='leadgroupid',
            field=models.ForeignKey(on_delete=django.db.models.deletion.CASCADE, to='webapp.user'),
        ),
    ]