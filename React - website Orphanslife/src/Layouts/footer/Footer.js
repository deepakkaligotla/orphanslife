import React from "react";
import { Box, Paper, Link } from '@mui/material'
import { styled } from '@mui/material/styles';
import kids_jumping from '../../Asset/images/kids_jumping.gif';
import { Grid } from '@mui/material';
import '../../Asset/Css/Body.css'

const Footer = () => {

  const Item = styled(Paper)(({ theme }) => ({
    backgroundColor: theme.palette.mode === 'dark' ? 'black' : 'black',
    ...theme.typography.body2,
    padding: theme.spacing(1),
    textAlign: 'center',
  }));

  return (
    <Box className="footerGridBox" sx={{ flexGrow: 1, width: '100%' }}>
      <Grid className="footerGridContainer" container spacing={3} style={{marginLeft: '1%',marginTop: '1%', marginBottom:'3%'}} sx={{ width: '100%' }}>
        <Item sx={{width:'inherit'}} style={{marginRight:'3%'}}>
          <h3 style={{
            color: "green",
            textAlign: "center"
          }}>
            Orphanslife: Orphanage Management System
          </h3>
          <Item style={{justifyContent:'center'}}>
            <img src={kids_jumping} alt="kids jumping" style={{ height: '25%', width: '25%' }}></img>
          </Item>
          <div className="footerrow" style={{display: 'grid', gridAutoFlow:'column'}}>
          <div className="footercolumn" style={{display: 'flex', flexDirection: 'column', textAlign: 'left'}}>
              <h4>About Us</h4>
              <Link className="footerLink" href="#">Aim</Link>
              <Link className="footerLink" href="#">Vision</Link>
              <Link className="footerLink" href="#">Testimonials</Link>
            </div>
            <div className="footercolumn" style={{display: 'flex', flexDirection: 'column', textAlign: 'left'}}>
              <h4>Services</h4>
              <Link className="footerLink" href="#">Writing</Link>
              <Link className="footerLink" href="#">Internships</Link>
              <Link className="footerLink" href="#">Coding</Link>
              <Link className="footerLink" href="#">Teaching</Link>
            </div>
            <div className="footercolumn" style={{display: 'flex', flexDirection: 'column', textAlign: 'left'}}>
              <h4>Contact Us</h4>
              <Link className="footerLink" href="#">Uttar Pradesh</Link>
              <Link className="footerLink" href="#">Ahemdabad</Link>
              <Link className="footerLink" href="#">Indore</Link>
              <Link className="footerLink" href="#">Mumbai</Link>
            </div>
            <div className="footercolumn" style={{display: 'flex', flexDirection: 'column', textAlign: 'left'}}>
              <h4>Social Media</h4>
              <Link className="footerLink" href="#">Facebook</Link>
              <Link className="footerLink" href="#">Instagram</Link>
              <Link className="footerLink" href="#">Twitter</Link>
              <Link className="footerLink" href="#">Youtube</Link>
            </div>
          </div>
        </Item>
      </Grid>
    </Box>
  );
};
export default Footer;