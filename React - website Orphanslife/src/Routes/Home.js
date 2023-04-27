import React from 'react';
import {Route, Routes} from 'react-router-dom';
import About from '../Layouts/About';
import Tables from '../Components/Tables';
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
import kids_jumping from '../Asset/images/kids_jumping.gif';

function Home(){
	return (<React.Fragment>
        <center>
              <div className="margin">
                    <Routes>
                        <Route path="/home" element={<Show/>}/>
                        <Route path="/tables" element={<Tables/>}/>
                        <Route path="/tables/sponsors" element={<Sponsors/>}/>
                        <Route path="/addsponsor" element={<AddSponsor/>} />
                        <Route path="/tables/admins" element={<Admins/>} />
                        <Route path="/tables/orphanages" element={<Orphanages/>} />
                        <Route path="/tables/locations" element={<Locations/>} />
                        <Route path="/tables/childs" element={<Childs/>} />
                        <Route path="/addchild" element={<AddChild/>} />
                        <Route path="/tables/adoptrequests" element={<AdoptRequests/>} />
                        <Route path="/tables/adoptstatus" element={<AdoptStatus/>} />
                        <Route path="/tables/donations" element={<Donations/>} />
                        <Route path="/tables/orphanage_activities" element={<OrphanageActivities/>} />
                        <Route path="/tables/roles" element={<Roles/>} />
                        <Route path="/about" element ={<About/>} />
                        <Route path="/*" element={<NotFound/>} />
                        <Route path="*" element={<NotFound/>} />
                        <Route path="/locationlivesearch" element={<LocationLiveSearch/>} />
                        <Route path="/logout" element={<Logout/>} />
                    </Routes>
			</div>
				<img src={kids_jumping} alt="kids jumping"></img>

           </center>
    </React.Fragment>
	)
};

export default Home;