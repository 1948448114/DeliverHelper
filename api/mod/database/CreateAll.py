#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Author  : LiangJ
#@Data     : 2015-08-06 10:38

import record
from db import engine, Base

Base.metadata.create_all(engine)