module.exports = ({logger}) => {
  return {
    search(text) {
      return [ {
        "name" : "Julia",
        "rank" : 0.1,
        "image" : "Julia.jpg",
        "education" : "Magister",
        "company" : "DB",
        "skills" : [ "Networking", "Controlling", "Innovation", "Digitalisierung" ],
        "projects" : [ "Pünktlichkeit", "Smart Rail" ]
      }, {
        "name" : "Romeo",
        "rank" : 99.8,
        "image" : "Romeo.jpg",
        "education" : "Ingenieur",
        "company" : "SBB",
        "skills" : [ "Projektmananagement", "Networking" ],
        "projects" : [ "Wlan im Zug", "Bahnhof" ]
      }, {
        "name" : "Hans",
        "rank" : 54.8,
        "image" : "Hans.jpg",
        "education" : "Bachelor of Science in Business",
        "company" : "DB",
        "skills" : [ "Sprachen", "Projekte" ],
        "projects" : [ "Zug" ]
      }, {
        "name" : "Vreni",
        "rank" : 14.8,
        "image" : "Vreni.jpg",
        "education" : "PhD in Finance",
        "company" : "ÖBB",
        "skills" : [ "Zahlen", "Projekte" ],
        "projects" : [ "Wlan" ]
      } ]
    }
  }
}
