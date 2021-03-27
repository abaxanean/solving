/*
 * Copyright 2021 Ivanti, Inc.
 * All rights reserved.
 */

package seekingalpha.v3;

import seekingalpha.Stock;

public interface Action {

    void execute(Stock stock);

}
