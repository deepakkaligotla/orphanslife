import React from 'react';
import {Route, Routes} from 'react-router-dom';
import About from '../Layouts/About';
import Sponsors from '../Components/Sponsors'
import AddSponsor from '../Components/Addsponsor'
import Admins from '../Components/Admins'
import AdoptRequests from '../Components/Adoptrequests'
import AdoptStatus from '../Components/Adoptstatus'
import Childs from '../Components/Childs'
import AddChild from '../Components/Addchild'
import Donations from '../Components/Donations'
import Locations from '../Components/Locations'
import Orphanages from '../Components/Orphanages'
import Roles from '../Components/Roles'
import Show from '../Layouts/Show'
import NotFound from '../Layouts/Notfound';
import Logout from '../Components/Logout';
import OrphanageActivities from '../Components/OrphanageActivities'
import LocationLiveSearch from '../Components/LocationLiveSearch';
import '../Asset/Css/Body.css';

function Home(){
	return (<React.Fragment>
        <center>
              <div className="margin">
                    <Routes>
                        <Route path="/home" element={<Show/>}/>
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
                        <Route path="/orphanage_activities" element={<OrphanageActivities/>} />
                        <Route path="/roles" element={<Roles/>} />
                        <Route path="/about" element ={<About/>} />
                        <Route path="/*" element={<NotFound/>} />
                        <Route path="*" element={<NotFound/>} />
                        <Route path="/locationlivesearch" element={<LocationLiveSearch/>} />
                        <Route path="/logout" element={<Logout/>} />
                    </Routes>
			</div>
				<img src="../../images/kids_jumping" alt="kids jumping"></img>

           </center>
    </React.Fragment>
	)
};

export default Home;