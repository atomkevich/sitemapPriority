# ---------------------NEW INVENTORY LINK -------------------
# number of site that hav't extracted new links
db.analizeMap.count({"extractedNewLinks": {$not: {$elemMatch: {"elements": /href/}}}})

# number of site that have extracted new links
db.analizeMap.count({"extractedNewLinks": {$elemMatch: {"elements": /href/}}})

#number of sites that haven't sitemap, but has link to inventory list
db.analizeMap.count({"errors": /client response code: 404/, "extractedNewLinks": {$elemMatch: {"elements": /href/}}})

#number of sites that haven't sitemap and links to inventory list
db.analizeMap.count({"errors": /client response code: 404/, "extractedNewLinks": {$not: {$elemMatch: {"elements": /href/}}}})

#-------------------------USED INVENTORY LINK ------------------------
# number of site that hav't extracted new links
db.analizeMap.count({"extractedUsedLinks": {$not: {$elemMatch: {"elements": /href/}}}})

# number of site that have extracted new links
db.analizeMap.count({"extractedUsedLinks": {$elemMatch: {"elements": /href/}}})

#number of sites that haven't sitemap, but has link to inventory list
db.analizeMap.count({"errors": /client response code: 404/, "extractedUsedLinks": {$elemMatch: {"elements": /href/}}})

#number of sites that haven't sitemap and links to inventory list
db.analizeMap.count({"errors": /client response code: 404/, "extractedUsedLinks": {$not: {$elemMatch: {"elements": /href/}}}})

# tota count of sites 
db.analizeMap.find()

#site haven't sitemap but have ..
db.analizeMap.count({
    $and: [
    {"errors": /client response code: 404/}, 
    {$or: [
        {"extractedUsedLinks": {$not: {$elemMatch: {"elements": /href/}}}},
        {"extractedNewLinks":  {$not:{$elemMatch: {"elements": /href/}}}}
        ]
     }
    
  ]})


