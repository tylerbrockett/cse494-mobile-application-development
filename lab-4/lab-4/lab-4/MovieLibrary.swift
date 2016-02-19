/*
 * @author                  Tyler Brockett	mailto:tylerbrockett@gmail.com
 * @course                  ASU CSE 494
 * @project                 Lab 4
 * @version                 Feb 18, 2016
 * @project description     Stores information about movies in a data structure and presents it to the user. Specifically, this project was to learn about TableView and Picker
 * @class description       Stores multiple instances of Movie. Contains methods to interface with the data.
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Tyler Brockett
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

import Foundation

class MovieLibrary {
    
    static let genres:[String] = ["Action", "Adventure", "Animation", "Biography", "Comedy", "Crime",
        "Documentary", "Drama", "Family", "Fantasy", "History", "Horror", "Music", "Musical",
        "Mystery", "Romance", "Sci-Fi", "Sports", "Thriller", "War", "Western"]

    var library:[Movie]
    
    init() {
        library = [Movie]()
        populateLibrary()
    }
    
    func add(movie:Movie) {
        library.append(movie)
    }
    
    func sort() {
        library.sortInPlace{(movie1, movie2) -> Bool in
            return movie1.title.lowercaseString < movie2.title.lowercaseString
        }
    }
    
    func remove(index:Int) {
        library.removeAtIndex(index)
    }
    
    func search(title:String) -> Movie {
        for movie in library {
            if movie.getTitle() == title {
                return movie
            }
        }
        return Movie(json:"")
    }
    
    func indexOf(title:String) -> Int {
        var i:Int = 0
        for movie in library {
            if movie.getTitle() == title {
                return i
            }
            i++
        }
        return -1
    }
    
    func getLibrary() -> [Movie] {
        return library
    }
    
    func get(index:Int) -> Movie {
        return library[index]
    }
    
    func clearAll() {
        library = []
    }
    
    func getSize() -> Int {
        return library.count
    }
    
    func getSizeFormatted() -> String {
        if getSize() == 1 {
            return "1 Entry"
        }
        return "\(getSize()) Entries"
    }
    
    /* TEMP: Replace with code reading in JSON data from file. */
    func populateLibrary() {
        add(Movie(json:"{\"Title\":\"Interstellar\",\"Year\":\"2014\",\"Rated\":\"PG-13\",\"Released\":\"07 Nov 2014\",\"Runtime\":\"169 min\",\"Genre\":\"Adventure\",\"Actors\":\"Ellen Burstyn, Matthew McConaughey, Mackenzie Foy, John Lithgow\",\"Plot\":\"A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.\"}"))
        add(Movie(json:"{\"Title\":\"Star Wars: Episode I - The Phantom Menace\",\"Year\":\"1999\",\"Rated\":\"PG\",\"Released\":\"19 May 1999\",\"Runtime\":\"136 min\",\"Genre\":\"Action\",\"Actors\":\"Liam Neeson, Ewan McGregor, Natalie Portman, Jake Lloyd\",\"Plot\":\"Two Jedi Knights escape a hostile blockade to find allies and come across a young boy who may bring balance to the Force, but the long dormant Sith resurface to reclaim their old glory.\"}"))
        add(Movie(json:"{\"Title\":\"Star Wars: Episode II - Attack of the Clones\",\"Year\":\"2002\",\"Rated\":\"PG\",\"Released\":\"16 May 2002\",\"Runtime\":\"142 min\",\"Genre\":\"Action\",\"Actors\":\"Ewan McGregor, Natalie Portman, Hayden Christensen, Christopher Lee\",\"Plot\":\"Ten years after initially meeting, Anakin Skywalker shares a forbidden romance with Padmé, while Obi-Wan investigates an assassination attempt on the Senator and discovers a secret clone army crafted for the Jedi.\"}"))
        add(Movie(json:"{\"Title\":\"Star Wars: Episode III - Revenge of the Sith\",\"Year\":\"2005\",\"Rated\":\"PG-13\",\"Released\":\"19 May 2005\",\"Runtime\":\"140 min\",\"Genre\":\"Action\",\"Actors\":\"Ewan McGregor, Natalie Portman, Hayden Christensen, Ian McDiarmid\",\"Plot\":\"Three years after the onset of the Clone Wars; the noble Jedi Knights are spread out across the galaxy leading a massive clone army in the war against the Separatists. After Chancellor ...\"}"))
        add(Movie(json:"{\"Title\":\"Star Wars: Episode IV - A New Hope\",\"Year\":\"1977\",\"Rated\":\"PG\",\"Released\":\"25 May 1977\",\"Runtime\":\"121 min\",\"Genre\":\"Action\",\"Actors\":\"Mark Hamill, Harrison Ford, Carrie Fisher, Peter Cushing\",\"Plot\":\"Luke Skywalker joins forces with a Jedi Knight, a cocky pilot, a wookiee and two droids to save the galaxy from the Empire's world-destroying battle-station, while also attempting to rescue Princess Leia from the evil Darth Vader.\"}"))
        add(Movie(json:"{\"Title\":\"Star Wars: Episode V - The Empire Strikes Back\",\"Year\":\"1980\",\"Rated\":\"PG\",\"Released\":\"20 Jun 1980\",\"Runtime\":\"124 min\",\"Genre\":\"Action\",\"Actors\":\"Mark Hamill, Harrison Ford, Carrie Fisher, Billy Dee Williams\",\"Plot\":\"After the rebels have been brutally overpowered by the Empire on their newly established base, Luke Skywalker takes advanced Jedi training with Master Yoda, while his friends are pursued by Darth Vader as part of his plan to capture Luke.\"}"))
        add(Movie(json:"{\"Title\":\"Star Wars: Episode VI - Return of the Jedi\",\"Year\":\"1983\",\"Rated\":\"PG\",\"Released\":\"25 May 1983\",\"Runtime\":\"131 min\",\"Genre\":\"Action\",\"Actors\":\"Mark Hamill, Harrison Ford, Carrie Fisher, Billy Dee Williams\",\"Plot\":\"After rescuing Han Solo from the palace of Jabba the Hutt, the rebels attempt to destroy the second Death Star, while Luke struggles to make Vader return from the dark side of the Force.\"}"))
        add(Movie(json:"{\"Title\":\"Star Wars: Episode VII - The Force Awakens\",\"Year\":\"2015\",\"Rated\":\"PG-13\",\"Released\":\"18 Dec 2015\",\"Runtime\":\"135 min\",\"Genre\":\"Action\",\"Actors\":\"Harrison Ford, Mark Hamill, Carrie Fisher, Adam Driver\",\"Plot\":\"Three decades after the defeat of the Galactic Empire, a new threat arises. The First Order attempts to rule the galaxy and only a ragtag group of heroes can stop them, along with the help of the Resistance.\"}"))
        add(Movie(json:"{\"Title\":\"Batman Begins\",\"Year\":\"2005\",\"Rated\":\"PG-13\",\"Released\":\"15 Jun 2005\",\"Runtime\":\"140 min\",\"Genre\":\"Action\",\"Actors\":\"Christian Bale, Michael Caine, Liam Neeson, Katie Holmes\",\"Plot\":\"After training with his mentor, Batman begins his war on crime to free the crime-ridden Gotham City from corruption that the Scarecrow and the League of Shadows have cast upon it.\"}"))
        add(Movie(json:"{\"Title\":\"The Dark Knight\",\"Year\":\"2008\",\"Rated\":\"PG-13\",\"Released\":\"18 Jul 2008\",\"Runtime\":\"152 min\",\"Genre\":\"Action\",\"Actors\":\"Christian Bale, Heath Ledger, Aaron Eckhart, Michael Caine\",\"Plot\":\"When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, the caped crusader must come to terms with one of the greatest psychological tests of his ability to fight injustice.\"}"))
        add(Movie(json:"{\"Title\":\"The Dark Knight Rises\",\"Year\":\"2012\",\"Rated\":\"PG-13\",\"Released\":\"20 Jul 2012\",\"Runtime\":\"164 min\",\"Genre\":\"Action\",\"Actors\":\"Christian Bale, Gary Oldman, Tom Hardy, Joseph Gordon-Levitt\",\"Plot\":\"Eight years after the Joker's reign of anarchy, the Dark Knight, with the help of the enigmatic Selia, is forced from his imposed exile to save Gotham City, now on the edge of total annihilation, from the brutal guerrilla terrorist Bane.\"}"))
        add(Movie(json:"{\"Title\":\"Inception\",\"Year\":\"2010\",\"Rated\":\"PG-13\",\"Released\":\"16 Jul 2010\",\"Runtime\":\"148 min\",\"Genre\":\"Action\",\"Actors\":\"Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page, Tom Hardy\",\"Plot\":\"A thief who steals corporate secrets through use of the dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.\"}"))
        add(Movie(json:"{\"Title\":\"Despicable Me\",\"Year\":\"2010\",\"Rated\":\"PG\",\"Released\":\"09 Jul 2010\",\"Runtime\":\"95 min\",\"Genre\":\"Animation\",\"Actors\":\"Steve Carell, Jason Segel, Russell Brand, Julie Andrews\",\"Plot\":\"When a criminal mastermind uses a trio of orphan girls as pawns for a grand scheme, he finds their love is profoundly changing him for the better.\"}"))
        add(Movie(json:"{\"Title\":\"Despicable Me 2\",\"Year\":\"2013\",\"Rated\":\"PG\",\"Released\":\"03 Jul 2013\",\"Runtime\":\"98 min\",\"Genre\":\"Animation\",\"Actors\":\"Steve Carell, Kristen Wiig, Benjamin Bratt, Miranda Cosgrove\",\"Plot\":\"When Gru, the world's most super-bad turned super-dad has been recruited by a team of officials to stop lethal muscle and a host of Gru's own, He has to fight back with new gadgetry, cars, and more minion madness.\"}"))
        add(Movie(json:"{\"Title\":\"Minions\",\"Year\":\"2015\",\"Rated\":\"PG\",\"Released\":\"10 Jul 2015\",\"Runtime\":\"91 min\",\"Genre\":\"Animation\",\"Actors\":\"Sandra Bullock, Jon Hamm, Michael Keaton, Allison Janney\",\"Plot\":\"Minions Stuart, Kevin and Bob are recruited by Scarlet Overkill, a super-villain who, alongside her inventor husband Herb, hatches a plot to take over the world.\"}"))
    }
}
