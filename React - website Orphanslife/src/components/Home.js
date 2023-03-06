import React from 'react';
import {BrowserRouter as Router, Route, Link, Routes} from 'react-router-dom';
import About from './About';
import Sponsors from './Sponsors'
import AddSponsor from './Addsponsor'
import Admins from './Admins'
import AdoptRequests from './Adoptrequests'
import AdoptStatus from './Adoptstatus'
import Childs from './Childs'
import AddChild from './Addchild'
import Donations from './Donations'
import Locations from './Locations'
import Orphanages from './Orphanages'
import Roles from './Roles'
import Show from './Show'
import NotFound from './Notfound';
import Logout from './Logout';
import LocationLiveSearch from './LocationLiveSearch';

function Home(){

	return (<center>
              <div class="margin">
                <hr></hr>
                    <Routes>
                        <Route path="/home" element={<Show/>}></Route>
                        <Route path="/sponsors" element={<Sponsors/>}/>
                        <Route path="/addsponsor" element={<AddSponsor/>} />
                        <Route path="/admins" element={<Admins/>} />
                        <Route path="/orphanages" element={<Orphanages/>} />
                        <Route path="/locations" element={<Locations/>} />
                        <Route path="/childs" element={<Childs/>} />
                        <Route path="/addchild" element={<AddChild/>} />
                        <Route path="/adoptrequests" element={<AdoptRequests/>} />
                        <Route path="/adoptstatus" element={<AdoptStatus/>} />
                        <Route path="/donations" element={<Donations/>} />
                        <Route path="/roles" element={<Roles/>} />
                        <Route path="/about" element ={<About/>} />
                        <Route path="/*" element={<NotFound/>} />
                        <Route path="*" element={<NotFound/>} />
                        <Route path="/locationlivesearch" element={<LocationLiveSearch/>} />
                        <Route path="/logout" element={<Logout/>} />
                    </Routes>
                <hr></hr>
			</div>
				<img src="https://orphanslife.s3.ap-northeast-1.amazonaws.com/kids_jumping_crop.gif" alt="kids jumping"></img>

           </center>
	)
};

export default Home;