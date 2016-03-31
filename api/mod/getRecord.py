from tornado.httpclient import HTTPRequest, AsyncHTTPClient
from sqlalchemy.orm.exc import NoResultFound
from database.record import Record
import tornado.web
import tornado.gen
import json, base64,traceback,urllib

class RecordListHandler(tornado.web.RequestHandler):

	@property
	def db(self):
		return self.application.db
	def on_finish(self):
		self.db.close()

	def post(self):
		retjson = {'code':200,'content':''}
		try:
			result = self.db.query(Record).filter(Record.state==1).all()
			content = []
			for item in result:
				temp = {
					'id':item.id,
					'name':item.name,
					'phone':item.phone,
					'detail':item.detail,
					'location':item.location
				}
				content.append(temp)
			retjson['content'] = content
		except Exception,e:
			retjson['code'] = 500
			retjson['content'] = str(e)
		ret = json.dumps(retjson, ensure_ascii=False, indent=2)
		self.write(ret)
		self.finish()
