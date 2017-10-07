from __future__ import unicode_literals
from django.db import models

# Create your models here.
class user(models.Model):
    id = models.BigAutoField(primary_key=True)
    name = models.CharField(max_length=300, blank=False)
    email = models.CharField(max_length=300, blank=False)
    password = models.CharField(max_length=300, blank=False)
    frendlist_id = models.BigIntegerField()
    block_id = models.BigIntegerField()
    '''đang onl thì true, off thì false'''
    status = models.BooleanField(default=False)

class conversation(models.Model):
    conversation_id = models.BigAutoField(primary_key=True)
    #0: không xóa tự động
    #1: xóa khi tất cả participants đã xem
    #x: xóa sau (x-1)*5p
    privacy = models.IntegerField(default=0)
    #leadgroupid có quyền thay đổi privacy của conversation
    #trong trường hợp 2 người, ai cũng có quyền thay đổi thông qua cấp hàm riêng
    leadgroupid = models.ForeignKey('webapp.user', on_delete=models.CASCADE,)

class paticipants(models.Model):
    paticipant_id = models.BigAutoField(primary_key=True)
    conversation_id = models.ForeignKey('webapp.conversation', on_delete=models.CASCADE,)
    user_id = models.ForeignKey('webapp.user', on_delete=models.CASCADE,)

class messages(models.Model):
    message_id = models.BigAutoField(primary_key=True)
    conversation_id = models.ForeignKey('webapp.conversation', on_delete=models.CASCADE,)
    from_id = models.ForeignKey('webapp.user', on_delete=models.CASCADE,)
    content = models.CharField(max_length=5000)
    timestamp_sent = models.BigIntegerField(default=0)
    # 0: không xóa tự động
    # 1: xóa khi tất cả participants đã xem
    # x: xóa sau (x-1)*5p
    privacyMessageLevel = models.IntegerField(default=0)
    numreaded = models.IntegerField(default=0)
    #file, bằng null nếu tin truyền đi không phải file
    fileup = models.FileField(upload_to='uploads/%Y/%m/%d/')



