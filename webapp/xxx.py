from webapp import models

newentry = models.user(name = "VuVanKhang", email = "sinhprous@gmail.com", password = "oicaiditmemay", frendlist_id = 1, block_id = 0, status = False)
newentry.save()