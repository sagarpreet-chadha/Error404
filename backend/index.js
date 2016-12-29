var express = require('express');
var bodyParser = require('body-parser');
var app = express();
var db = require('./Db.js');

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

app.post("/useradd",function(req,res) {
	console.log("User wants to add an item");
	var length = req.body.length;
	for(var x =0;x<length;x++)
	{
		db.Create("table_user","email,item,category","'"+"req.body[x].email"+"','"+req.body[x].name+"','"+req.body[x].category+"'",function(err,data){
				if(err)
				{
					console.log("err");
					console.log(data);
					res.end("Failed");
				}
		});
	}
	res.end("Success");
})
app.post("/userremove",function(req,res){
	var length = req.body.length;
	for(var i =0;i<length;i++)
	{
		console.log("I'm here");
		db.Delete(" table_user "," email = '" + "req.body[i].email" + "' AND item = '" + req.body[i].name+ "' AND category = '"+ req.body[i].category+"'",function(err,data){
			console.log("Delete");
			if(err)
			{
				console.log(err);
				console.log(data);
				res.end("Failed userRemove");
			}
		})
	}
	res.end("Success");
})
app.post("/beaconadd",function(req,res){
	console.log("some one wants to add beaconDetail");
	var length = req.body.length;
	for(var i = 0; i<length;i++)
	{
		db.Create("table_beacon","uuid,shop,item,category","'"+req.body[i].uuid+"','"+req.body[i].shop+"','"+req.body[i].name+"','"+req.body[i].category+"'",function(err,data){
			if(err){
				console.log("err");
				console.log(data);
				res.end("Failed");
			}
		})
	}
	res.end("Success");
})
app.post("/beaconremove",function(req,res){
	console.log("Someone wants to remove beacon beaconDetail");
	var length = req.body.length;
	for(var i =0;i<length;i++){
		db.Delete("table_beacon","uuid = '"+req.body[i].uuid+"' AND shop = '"+req.body[i].shop+"' AND item = '"+req.body[i].name+"' AND category = '"+req.body[i].category+"'",function(err,data){
			if (err) {
				console.log("Err");
				console.log(data);
				res.end("Failed");
			}
		})
	}
	res.end("Success");	
})
app.listen(3000);
console.log('connected to localhost....');