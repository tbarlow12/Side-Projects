# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('uteratings', '0001_initial'),
    ]

    operations = [
        migrations.AddField(
            model_name='professor',
            name='name',
            field=models.CharField(default=b'', max_length=200),
        ),
    ]
