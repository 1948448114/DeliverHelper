from tornado.httpclient import HTTPRequest, AsyncHTTPClient
from sqlalchemy.orm.exc import NoResultFound
from database.record import Record
import tornado.web
import tornado.gen
import json, base64,traceback,urllib

class NewRecordHandler(tornado.web.RequestHandler):

	@property
	def db(self):
		return self.application.db
	def on_finish(self):
		self.db.close()

	def post(self):
		retjson = {'code':200,'content':'ok'}
		try:
			item = Record(detail=self.get_argument('detail'),location=self.get_argument('location'),phone=self.get_argument('phone'),name=self.get_argument('name'),state=1)
			self.db.add(item)
			self.db.commit()
		except Exception,e:
			retjson['code'] = 500
			retjson['content'] = str(e)
		ret = json.dumps(retjson, ensure_ascii=False, indent=2)
		self.write(ret)
		self.finish()
