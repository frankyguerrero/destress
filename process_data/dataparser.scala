// TO RUN WITH BIDMACH DO:
// $ /opt/BIDMach_1.0.0-full-linux-x86_64/bidmach utils.scala examples.scala


import utils._  // utility methods

val dir = "/var/local/destress/tokenized/";
val xmlFile = loadIMat(dir+"zz.xml.imat");
val newdict = loadDict(dir+"zz_dict.sbmat",dir+"zz_dict.imat")


val usersIdx = getBeginEnd(xmlFile, newdict, "posts");
val postIdx = getBeginEnd(xmlFile, newdict, "post");
val eventIdx = getBeginEnd(xmlFile, newdict, "event");
val moodIdx = getBeginEnd(xmlFile, newdict, "current_moodid");


// Get nrUsers
var nrUsers = 0
if (usersIdx(0,0) != -1) {nrUsers = usersIdx.nrows}


// Get nrValidPosts
// Posts where <event><string>...</string></event>
// If </string> is not parsed properly, discard
val validEvent = find(xmlFile(eventIdx(?,1)-1) == newdict("</string>"));
// If don't want to discard just append
// find(xmlFile(eventIdx(?,1)-1) == newdict("string"));

val nrValidPosts = validEvent.nrows;
// valEventIdx points to <string> +1 and to </string>: do col(0)->col(1)
var valEventIdx = eventIdx(validEvent, ?) + (1\ -1);

val valpostIdx = postIdx(validEvent,?); //assumes postIdx.nrows == eventIdx.nrows


// Get nrWords in dictionary
val nrWords = newdict.cstr.nrows;


// Initialize structures
// For sparse posts
var sBoWposts = sparse(izeros(nrWords,0))

// For now, IMat with (UserId, CurrentMoodId) - later do datetime+replycount
var labels = izeros(2, nrValidPosts);

var posti = 0; // iteration counter for while
var userk = 0; // current userid
while (posti < nrValidPosts) {

      // Increment userk
      // for that use info in user[s]Idx and valpostIdx

      // Check if moodid exist
      // if yes work on it

      	 // Add to "labels" -> userid, currentmoodId


	 // Add sparse column of current post BoW, to full BoW     	 
	 var postWordId = getWordsOnly(xmlFile, valEventIdx(posti,0), valEventIdx(posti,1));
	 temp = sparse(postWordId,izeros(postWordId.nrows,1),iones(postWordId.nrows,1), nrWords,1);
	 sBoWposts \= temp; // horizontal concatenation of another post's bag-of-words


      // Next Post
      posti += 1;
}





//COMMENTS ONLY

//Get not a number values
//val ind = xmlFile(moodIdx(1,0)+find(xmlFile(moodIdx(1,0)->moodIdx(1,1))>0))
//newdict(ind)
//Get number values -> need to be converted
//val neg = xmlFile(moodIdx(1,0)+find(xmlFile(moodIdx(1,0)->moodIdx(1,1))<0))
//twoComplementToInt(neg)


// moodId -> integer 1->134 (no 94 nor 50)
//sum(newdict.counts)
//maxi(newdict.counts)
