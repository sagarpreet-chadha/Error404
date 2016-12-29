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
		db.Create("table_beacon","instance_id,shop,item,category","'"+req.body[i].instance_id+"','"+req.body[i].shop+"','"+req.body[i].name+"','"+req.body[i].category+"'",function(err,data){
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
		db.Delete("table_beacon","instance_id = '"+req.body[i].instance_id+"' AND shop = '"+req.body[i].shop+"' AND item = '"+req.body[i].name+"' AND category = '"+req.body[i].category+"'",function(err,data){
			if (err) {
				console.log("Err");
				console.log(data);
				res.end("Failed");
			}
		})
	}
	res.end("Success");	
})
app.get("/beacon",function(req,res){
	var instance_id = req.param("instance_id");
	var cat = [];
	db.Read("table_beacon","*","instance_id = '"+instance_id+"'",function(err,data){
		length = data.length;
		if(err)
		{
			console.log("error");
			console.log(data);
			res.end("Failed");
			return;
		}
		else if(length != 0)
		{
			var x =1;
			for(var i = 0;i < length;i++)
			{
				var category = data[i].category;
				var item = data[i].item;
				var shop = data[i].shop;
				db.Read("table_user","*","category = '"+category+"' AND item = '"+item+"'",function(err,items)
				{
					if(err)
					{
						console.log("err");
						console.log(items);
						res.end("Failed");
						x++;
						return;
					}
					else if (items.length != 0){
						//code..
						console.log(item);
						cat.push({instance_id : instance_id,
							category : category,
							name : item,
							shop : shop});
						console.log(cat);
						console.log("I'm done");
						x++;
					}
					else{
						console.log("not matched");
						x++;
					}
					if(x == length+1){
						console.log("x==  "+ x);
						console.log(cat);
						console.log("hey")
						var json = JSON.stringify({ 
			    				ItemsAvailable: cat
			  				});
			  			res.end(json); 
					}
				})
			}
		}
		else{
			console.log("Not found");
			res.end("Item not found");
			return;
		}
		
	})
})
app.listen(3000);
console.log('connected to localhost....');