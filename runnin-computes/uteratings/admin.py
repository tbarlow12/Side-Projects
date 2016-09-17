from django.contrib import admin

from .models import Professor

# Register your models here.
class ProfessorAdmin(admin.ModelAdmin):
	fieldsets = [
		(None,			{'fields': ['name']	}),
		('Rating Data', {'fields': ['rating_total', 'num_of_ratings'], 'classes': ['collapse']}),
	]

	list_display = ('name', 'rating')

admin.site.register(Professor, ProfessorAdmin)