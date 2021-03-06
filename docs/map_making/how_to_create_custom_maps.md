
The initial settings for each game are stored in a file that you can
edit to create a new game.&nbsp; You can modify unit placement,
territory ownership, number of players, alliance of players, turn
order, strengths of units, and other factors.<br>
<br>
<h2>Editing the file</h2>
Before you start make a copy of classic_a&amp;a.xml and rename it
mygame.xml.&nbsp; You can modify this file without changing the way the
game works normally.<br>
<br>
You can open the file in any text editor.&nbsp; On windows you can use
Notepad.&nbsp; Simple start notepad, and then use the file-&gt; open
menu.&nbsp; When you save the game, make sure that you save the game
with the correct extension (.xml).&nbsp; When saving use file-&gt;save
as, and before you save change the "Save as Type" to be "All Files".<br>
<br>
<h2>Sanity check<br>
</h2>
Many of the values you can modify make no sense, and may cause the game
to become unstable while playing.&nbsp; It is possiible for instance to
specify an attack value of 10 for an infantry unit, or to say that a
nation has two capitals, or none.&nbsp; Needless to say this may cause
the game to act in weird ways.&nbsp; This may be what you want, but be
warned.<br>
<br>
<h2>Modifying unit placement</h2>
Look in the file for the line,
<pre><code>&lt;unitInitialize&gt;</code></pre>
After that line you will see a series of lines that look something like,<br>
<pre><code>&lt;unitPlacement unitType="infantry" territory="Alaska" quantity ="1" owner="Americans" /&gt;</code></pre>
This line simply says to place 1 infantry in Alaska owned by the
americans.&nbsp; You can add, modify or delete lines to change where
units are placed.&nbsp; Be sure to get the spelling of the players and
the territories right.
<h2>Modifying territory ownership</h2>
Look in the file for the line,
<pre><code>&lt;ownerInitialize&gt;</code></pre>
After this line you will see a series of lines like
<pre><code>&lt;territoryOwner territory="Alaska" owner = "Americans" /&gt;</code></pre><br>
You can modify these lines to change the starting ownership of each
country.&nbsp; If you want a country to be neutral then remove the line
for that territory.&nbsp; If a country is neutral, add a new line to
set it's ownership/<br>
<br>
<h2>Changing player alliances</h2>
Look in the file for the line,
<pre><code>&lt;playerList&gt;</code></pre>
You will see a&nbsp; series of lines like,
<pre><code>&lt;alliance player="Germans" alliance="Axis" /&gt;</code></pre>
To switch the german player to be allied, you would simply change this
line to be
<pre><code>&lt;alliance player="Germans" alliance="Allies" /&gt;</code></pre><br>
<h2>Changing unit strengths</h2>
To change the attack, defense or movement points of a unit look for a
line that looks like
<pre><code>&lt;attachmentList&gt;</code></pre><br>
Beneath that you will see a series of lines like<br>
<pre><code>&lt;attachment name="unitAttachment" attatchTo="infantry" javaClass="games.strategy.triplea.attachments.UnitAttachment" type="unitType"&gt;
  &lt;option name="movement" value="1" /&gt;
  &lt;option name="transportCost" value="1" /&gt;
  &lt;option name="attack" value="1" /&gt;
  &lt;option name="defense" value="2" /&gt;
&lt;/attachment&gt;</code></pre>
You can modify these lines to change the attack, defence and
movement of an infantry.&nbsp; You can make similiar adjustments for
the other unit types.<br>
<h2>Changing territory production<br>
</h2>
To change the production value of the Caucus, look for a line that
looks like
<pre><code>&lt;attachment name="territoryAttachment" attatchTo="Caucasus" javaClass="games.strategy.triplea.attachments.TerritoryAttachment" type="territory"&gt;
  <span style="font-weight: bold;">&lt;option name="production" value="3" /&gt;</span>
&lt;/attachment&gt;<br>
</code></pre>
By changing the line <br>
<pre><code>&lt;option name="production" <span style="font-weight: bold;">value="3"</span>/&gt;</code></pre>
to
<pre><code>&lt;option name="production" <span style="font-weight: bold;">value="10"</span> /&gt;</code></pre>
You will change the production of the caucus to 10.
<h2>Changing capital placement</h2>
You can change the location of the capitals for each player by moving a
line.&nbsp; The capital for the us is specified in the capital setting
below<br>
<pre><code>&lt;attachment name="territoryAttachment" attatchTo="East US" javaClass="games.strategy.triplea.attachments.TerritoryAttachment" type="territory"&gt;
  &lt;option name="production" value="12" /&gt;
  &lt;option name="originalFactory" value="true" /&gt;
  <span style="font-weight: bold;">&lt;option name="capital" value="Americans" /&gt;</span>
&lt;/attachment&gt;</code></pre>
By moving this line to another territory, you will change the capital
of the US to a different state.<br>
<h2>Changing turn sequence</h2>
You can change the sequence of who moves when.<br>
If you look for the line
<pre><code>&lt;sequence&gt;</code></pre>
Below that are a series of lines like
<pre><code>&lt;step name="russianTech" delegate="tech" player="Russians" /&gt;<br></code></pre>
The game will run the steps in the order listed.&nbsp; To make
the Russians move after the japanese, simply move all the russian step
lines after the japanese step lines.<br>
<h2>And much more</h2>
You can write code to change the behaviour of the game in ways that the
author never intended.&nbsp; The source code is
released under the gpl, and you are free to download it and modify it
as long as you follow the terms of the gpl.<br>
