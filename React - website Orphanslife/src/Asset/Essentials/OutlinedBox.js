import React from "react";
import {Box, FormLabel} from "@mui/material";

const OutlinedBox = (props) => {
  const {
    label,
    children
  } = props;
  return (
    <Box>
      <FormLabel
        sx={{
          marginLeft: "0.71em",
          marginTop: "0.71em",
          paddingLeft: "0.44em",
          paddingRight: '0.44em',
          zIndex: 2,
          backgroundColor: (theme) => theme.palette.background.default,
          position: "absolute",
          fontSize: "0.75em",
          width: 'auto',
        }}>{label}</FormLabel>
      <Box
        sx={{
          position: 'relative',
          borderRadius: theme => theme.shape.borderRadius + 'px',
          fontSize: '0.875rem',
        }}
      >
        <Box
          sx={{
            padding: (theme) => theme.spacing(1),
            display: 'flex',
            gap: (theme) => theme.spacing(1),
            flexWrap: 'wrap',
            overflow: 'auto'
          }}
        >
          {children}
        </Box>
        <fieldset aria-hidden={"true"} style={{
          textAlign: 'left',
          position: 'absolute',
          bottom: 0,
          right: 0,
          top: '-5px',
          left: 0,
          margin: 0,
          padding: '0 8px',
          pointerEvents: 'none',
          borderRadius: 'inherit',
          borderStyle: 'solid',
          borderWidth: '1px',
          overflow: 'hidden',
          minWidth: '0%',
          borderColor: 'rgba(255, 255, 255, 0.23)',
        }}
        >
          <legend style={{
            float: 'unset',
            overflow: 'hidden',
            display: 'block',
            width: 'auto',
            padding: 0,
            height: '11px',
            fontSize: '0.75em',
            visibility: 'hidden',
            maxWidth: '100%',
            'WebkitTransition': 'max-width 100ms cubic-bezier(0.0, 0, 0.2, 1) 50ms',
            transition: 'max-width 100ms cubic-bezier(0.0, 0, 0.2, 1) 50ms',
            whiteSpace: 'nowrap',
          }}><span>{label}</span></legend>
        </fieldset>
      </Box>
    </Box>
  );
}

export default OutlinedBox;