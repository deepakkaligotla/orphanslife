const express = require("express");
const auth = require("../Auth/auth");
const { admin, editor, viewer } = require("../Auth/roles");
const os = require('os')
const db = require('../db.js')

let messages = [{ id: 1, name: "Lorem ipsum dolor", content: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras pretium nec ipsum nec elementum." }];

const router = express.Router();

router.get("/", (req, res) => {
    //System info
    const os_type =os.type()
    const sys_architecture=os.arch()

    const cpu_info_model=os.cpus()[0]['model']
    const cpu_info_speed=os.cpus()[0]['speed']
    const cpu_info_times_user=os.cpus()[0]['times']['user']
    const cpu_info_times_nice=os.cpus()[0]['times']['nice']
    const cpu_info_times_sys=os.cpus()[0]['times']['sys']
    const cpu_info_times_idle=os.cpus()[0]['times']['idle']
    const cpu_info_times_irq=os.cpus()[0]['times']['irq']

    const network_interface_base_address='lo0 - ['+os.networkInterfaces()['lo0'][0]['address']+' || '+os.networkInterfaces()['lo0'][1]['address']+' || '+os.networkInterfaces()['lo0'][2]['address']+']'+
    'anpi1 - '+os.networkInterfaces()['anpi1'][0]['address']+'  anpi2 - '+os.networkInterfaces()['anpi2'][0]['address']+
    ' anpi0 - '+os.networkInterfaces()['anpi0'][0]['address']+'  en0 - ['+os.networkInterfaces()['en0'][0]['address']+
    ' || '+os.networkInterfaces()['en0'][1]['address']+' || '+os.networkInterfaces()['en0'][2]['address']+
    ' || '+os.networkInterfaces()['en0'][2]['address']+']  awdl0 - '+os.networkInterfaces()['awdl0'][0]['address']+
    '] llw0 - '+os.networkInterfaces()['llw0'][0]['address']+']  utun0 - ['+os.networkInterfaces()['utun0'][0]['address']+
    '] utun1 - ['+os.networkInterfaces()['utun1'][0]['address']+']  utun2 - ['+os.networkInterfaces()['utun2'][0]['address']+
    ']'

    const network_interface_base_netmask='lo0 - ['+os.networkInterfaces()['lo0'][0]['netmask']+' || '+os.networkInterfaces()['lo0'][1]['netmask']+' || '+os.networkInterfaces()['lo0'][2]['netmask']+']'+
    'anpi1 - '+os.networkInterfaces()['anpi1'][0]['netmask']+'  anpi2 - '+os.networkInterfaces()['anpi2'][0]['netmask']+
    ' anpi0 - '+os.networkInterfaces()['anpi0'][0]['netmask']+'  en0 - ['+os.networkInterfaces()['en0'][0]['netmask']+
    ' || '+os.networkInterfaces()['en0'][1]['netmask']+' || '+os.networkInterfaces()['en0'][2]['netmask']+
    ' || '+os.networkInterfaces()['en0'][2]['netmask']+']  awdl0 - '+os.networkInterfaces()['awdl0'][0]['netmask']+
    '] llw0 - '+os.networkInterfaces()['llw0'][0]['netmask']+']  utun0 - ['+os.networkInterfaces()['utun0'][0]['netmask']+
    '] utun1 - ['+os.networkInterfaces()['utun1'][0]['netmask']+']  utun2 - ['+os.networkInterfaces()['utun2'][0]['netmask']+
    ']'

    const network_interface_base_family='lo0 - ['+os.networkInterfaces()['lo0'][0]['family']+' || '+os.networkInterfaces()['lo0'][1]['family']+' || '+os.networkInterfaces()['lo0'][2]['family']+']'+
    'anpi1 - '+os.networkInterfaces()['anpi1'][0]['family']+'  anpi2 - '+os.networkInterfaces()['anpi2'][0]['family']+
    ' anpi0 - '+os.networkInterfaces()['anpi0'][0]['family']+'  en0 - ['+os.networkInterfaces()['en0'][0]['family']+
    ' || '+os.networkInterfaces()['en0'][1]['family']+' || '+os.networkInterfaces()['en0'][2]['family']+
    ' || '+os.networkInterfaces()['en0'][2]['family']+']  awdl0 - '+os.networkInterfaces()['awdl0'][0]['family']+
    '] llw0 - '+os.networkInterfaces()['llw0'][0]['family']+']  utun0 - ['+os.networkInterfaces()['utun0'][0]['family']+
    '] utun1 - ['+os.networkInterfaces()['utun1'][0]['family']+']  utun2 - ['+os.networkInterfaces()['utun2'][0]['family']+
    ']'

    const network_interface_base_mac='lo0 - ['+os.networkInterfaces()['lo0'][0]['mac']+' || '+os.networkInterfaces()['lo0'][1]['mac']+' || '+os.networkInterfaces()['lo0'][2]['mac']+']'+
    'anpi1 - '+os.networkInterfaces()['anpi1'][0]['mac']+'  anpi2 - '+os.networkInterfaces()['anpi2'][0]['mac']+
    ' anpi0 - '+os.networkInterfaces()['anpi0'][0]['mac']+'  en0 - ['+os.networkInterfaces()['en0'][0]['mac']+
    ' || '+os.networkInterfaces()['en0'][1]['mac']+' || '+os.networkInterfaces()['en0'][2]['mac']+
    ' || '+os.networkInterfaces()['en0'][2]['mac']+']  awdl0 - '+os.networkInterfaces()['awdl0'][0]['mac']+
    '] llw0 - '+os.networkInterfaces()['llw0'][0]['mac']+']  utun0 - ['+os.networkInterfaces()['utun0'][0]['mac']+
    '] utun1 - ['+os.networkInterfaces()['utun1'][0]['mac']+']  utun2 - ['+os.networkInterfaces()['utun2'][0]['mac']+
    ']'

    const network_interface_base_scopeid='lo0 - ['+os.networkInterfaces()['lo0'][0]['scopeid']+' || '+os.networkInterfaces()['lo0'][1]['scopeid']+' || '+os.networkInterfaces()['lo0'][2]['scopeid']+']'+
    'anpi1 - '+os.networkInterfaces()['anpi1'][0]['scopeid']+'  anpi2 - '+os.networkInterfaces()['anpi2'][0]['scopeid']+
    ' anpi0 - '+os.networkInterfaces()['anpi0'][0]['scopeid']+'  en0 - ['+os.networkInterfaces()['en0'][0]['scopeid']+
    ' || '+os.networkInterfaces()['en0'][1]['scopeid']+' || '+os.networkInterfaces()['en0'][2]['scopeid']+
    ' || '+os.networkInterfaces()['en0'][2]['scopeid']+']  awdl0 - '+os.networkInterfaces()['awdl0'][0]['scopeid']+
    '] llw0 - '+os.networkInterfaces()['llw0'][0]['scopeid']+']  utun0 - ['+os.networkInterfaces()['utun0'][0]['scopeid']+
    '] utun1 - ['+os.networkInterfaces()['utun1'][0]['scopeid']+']  utun2 - ['+os.networkInterfaces()['utun2'][0]['scopeid']+
    ']'

    const network_interface_base_internal='lo0 - ['+os.networkInterfaces()['lo0'][0]['internal']+' || '+os.networkInterfaces()['lo0'][1]['internal']+' || '+os.networkInterfaces()['lo0'][2]['internal']+']'+
    'anpi1 - '+os.networkInterfaces()['anpi1'][0]['internal']+'  anpi2 - '+os.networkInterfaces()['anpi2'][0]['internal']+
    ' anpi0 - '+os.networkInterfaces()['anpi0'][0]['internal']+'  en0 - ['+os.networkInterfaces()['en0'][0]['internal']+
    ' || '+os.networkInterfaces()['en0'][1]['internal']+' || '+os.networkInterfaces()['en0'][2]['internal']+
    ' || '+os.networkInterfaces()['en0'][2]['internal']+']  awdl0 - '+os.networkInterfaces()['awdl0'][0]['internal']+
    '] llw0 - '+os.networkInterfaces()['llw0'][0]['internal']+']  utun0 - ['+os.networkInterfaces()['utun0'][0]['internal']+
    '] utun1 - ['+os.networkInterfaces()['utun1'][0]['internal']+']  utun2 - ['+os.networkInterfaces()['utun2'][0]['internal']+
    ']'

    const network_interface_base_cidr='lo0 - ['+os.networkInterfaces()['lo0'][0]['cidr']+' || '+os.networkInterfaces()['lo0'][1]['cidr']+' || '+os.networkInterfaces()['lo0'][2]['cidr']+']'+
    'anpi1 - '+os.networkInterfaces()['anpi1'][0]['cidr']+'  anpi2 - '+os.networkInterfaces()['anpi2'][0]['cidr']+
    ' anpi0 - '+os.networkInterfaces()['anpi0'][0]['cidr']+'  en0 - ['+os.networkInterfaces()['en0'][0]['cidr']+
    ' || '+os.networkInterfaces()['en0'][1]['cidr']+' || '+os.networkInterfaces()['en0'][2]['cidr']+
    ' || '+os.networkInterfaces()['en0'][2]['cidr']+']  awdl0 - '+os.networkInterfaces()['awdl0'][0]['cidr']+
    '] llw0 - '+os.networkInterfaces()['llw0'][0]['cidr']+']  utun0 - ['+os.networkInterfaces()['utun0'][0]['cidr']+
    '] utun1 - ['+os.networkInterfaces()['utun1'][0]['cidr']+']  utun2 - ['+os.networkInterfaces()['utun2'][0]['cidr']+
    ']'

    const user_info_username=os.userInfo()['username']
    const user_info_uid=os.userInfo()['uid']
    const user_info_gid=os.userInfo()['gid']
    const user_info_shell=os.userInfo()['homedir']
    const user_info_homedir=os.userInfo()['shell']

    const hostname=os.hostname()
    const loadavg='loadavg - ['+os.loadavg()[0]+', '+os.loadavg()[1]+', '+os.loadavg()[2]+']'
    const uptime=os.uptime()
    const free_memory=os.freemem()
    const total_memory=os.totalmem()
    const available_parallelism=os.availableParallelism()
    const home_directory=os.homedir()
    const version=os.version()
    const platform=os.platform()
    const machine=os.machine()
    const temp_directory=os.tmpdir()
    const endianness=os.endianness()
    const get_priorities=os.getPriority(1)
    const set_priority=os.setPriority(1)
  
    const saveUserMachine = `insert into user_machine (
        os_type, 
        sys_architecture, 
        cpu_info_model, 
        cpu_info_speed, 
        cpu_info_times_user, 
        cpu_info_times_nice, 
        cpu_info_times_sys, 
        cpu_info_times_idle, 
        cpu_info_times_irq,
        network_interface_base_address, 
        network_interface_base_netmask, 
        network_interface_base_family, 
        network_interface_base_mac, 
        network_interface_base_scopeid, 
        network_interface_base_internal, 
        network_interface_base_cidr,
        user_info_username, 
        user_info_uid, 
        user_info_gid, 
        user_info_homedir, 
        user_info_shell, 
        hostname,
        loadavg,
        uptime,
        free_memory, 
        total_memory,
        available_parallelism,
        home_directory,
        version,
        platform,
        machine,
        temp_directory,
        endianness,
        get_priorities,
        set_priority
      ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)`
    db.pool.query(saveUserMachine, [
        os_type, 
        sys_architecture, 
        cpu_info_model, 
        cpu_info_speed, 
        cpu_info_times_user, 
        cpu_info_times_nice, 
        cpu_info_times_sys, 
        cpu_info_times_idle, 
        cpu_info_times_irq,
        network_interface_base_address, 
        network_interface_base_netmask, 
        network_interface_base_family, 
        network_interface_base_mac, 
        network_interface_base_scopeid, 
        network_interface_base_internal, 
        network_interface_base_cidr,
        user_info_username, 
        user_info_uid, 
        user_info_gid, 
        user_info_homedir, 
        user_info_shell, 
        hostname,
        loadavg,
        uptime,
        free_memory, 
        total_memory,
        available_parallelism,
        home_directory,
        version,
        platform,
        machine,
        temp_directory,
        endianness,
        get_priorities,
        set_priority
    ], (error, result) => {
        console.log(result)
        console.log(error)
    })
    res.send({
        ok: true,
        result: messages,
        localIP: os.networkInterfaces()['en0'][1]['address']
    });
});

router.post("/", [auth, editor], async (req, res) => {
    messages.push({ id: messages.length + 1, name: req.body.name, content: req.body.content });
    res.status(200).send({
        ok: true,
        result: messages
    });
});

router.put("/", [auth, editor], async (req, res) => {
    res.status(200).send({
        ok: true,
        result: messages
    });
});

router.post("/", [auth, admin], async (req, res) => {
    messages.push({ id: messages.length + 1, name: req.body.name, content: req.body.content });
    res.status(200).send({
        ok: true,
        result: messages
    });
});

router.put("/", [auth, admin], async (req, res) => {
    res.status(200).send({
        ok: true,
        result: messages
    });
});

router.delete("/", [auth, admin], async (req, res) => {
    messages = messages.filter((message) => { message.id !== req.body.id });
    res.status(200).send({
        ok: true,
        result: messages
    });
});

module.exports = router;