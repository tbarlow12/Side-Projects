from django.db import models

# Create your models here.
class Professor(models.Model):
	name = models.CharField(default="", max_length=200)
	rating_total = models.IntegerField(default=0)
	num_of_ratings = models.IntegerField(default=0)

	num_of_ratings.short_description = 'Number of Ratings'

	def rating(self):
		if self.num_of_ratings != 0:
			return '%.2f' % (float(self.rating_total) / float(self.num_of_ratings))
		else:
			return 'No Ratings';

	rating.admin_order_field = 'num_of_ratings'

	def __str__(self):
		return self.name

	def __unicode__(self):
		return self.name