import React, { FC } from "react";
import { Callout } from "@blueprintjs/core";

interface Props {
  error: string;
}

export const Error: FC<Props> = ({ error }) => {
  return (
    <Callout intent="danger" className="panel">
      {error}
    </Callout>
  );
};
